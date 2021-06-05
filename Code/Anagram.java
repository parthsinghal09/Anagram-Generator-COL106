import java.io.*; 
import java.util.*;

class h_node{

	public String key; // characters of multiset in sorted order
	public Vector<String> value;

	public h_node(String k, String s){

		key = k; 
		value = new Vector<String>();
		value.add(s); 

	}
}

// hash table = [node(multiset(key), values(words))]

class hash_table{


    public int table_size;
    public Vector<Integer> hash_values;
	public Vector<h_node> hash_array;

	public hash_table(int n){

        table_size = n;
        
		hash_array = new Vector<h_node>();

        hash_values = new Vector<Integer>();
        for(int i = 0; i<256; i++){

            hash_values.add(get_hash_value(i));

        }

		for(int i = 0; i<n; i++){

			hash_array.add(null);

		}
	}

    public int get_hash_value(int ch){

        ch = ch*6942059 + 5943073;
        // right bit shift followed by XOR operation
        ch = (ch^(ch>>29))*6793817;
 		ch = (ch^(ch>>28))*7794817;
 		ch = (ch^(ch>>27))*8795817;
 		ch = (ch^(ch>>26))*9796817;
        ch = ch^(ch>>31);

        // to check for overflow
        if(ch<0){

            ch = -1*ch;

        }

        return ch;
    }

    
    public int hash_function(String s){
        // s is sorted string to represent multiset
        int sum = 0;

        for(int i = 0; i<s.length(); i++) {

            sum = sum + hash_values.get((int)s.charAt(i));

        }

        return sum;
    }

    public String sort_string(String s){

        char c[] = s.toCharArray();
          
        Arrays.sort(c);

        String sorted_s = new String(c);

        return sorted_s;  
    }

    public void insert(String s){
    
        // insert given word from the vocab in our hash-table

        String s_key = sort_string(s);

        // s should be sorted

        int idx = hash_function(s)%table_size;

        if(idx<0) {

            idx = idx + table_size;

        }

        while(hash_array.get(idx)!=null && !hash_array.get(idx).key.equals(s_key)){
            
            idx++;

            if(idx>=table_size){
                
                idx = 0;

            }

        }

        if(hash_array.get(idx)!=null){

            //element already present
            hash_array.get(idx).value.add(s);
            
        } else{
            
            // make new h_node
            h_node hash_node = new h_node(s_key, s);
            hash_array.set(idx, hash_node);
            
        }
    }

    public h_node search(String s){

        // s_key shuould be sorted
        String s_key = sort_string(s);

        int idx = hash_function(s_key)%table_size;

        if(idx<0){

            idx =  idx + table_size;

        }

        while(hash_array.get(idx)!=null && !hash_array.get(idx).key.equals(s_key)){

            idx++;
            if(idx>=table_size){

                idx = 0;

            }

        }

        return hash_array.get(idx); // returns null if not present.
    }
}

class space_anagram_generator{

    
    public Vector<String> all_anagrams;

    public int[] curr_track;

    public String given_string;

    int count;

    public space_anagram_generator(String s){

        all_anagrams = new Vector<String>();
        curr_track = new int[s.length()];
        given_string = s;
        count = 0;
    }
    

    public void generate_anagrams(int idx, int strlen) {

        if(idx == strlen){

            String s1 = "";
            String s2 = "";
            String s3 = "";
            StringBuilder sb = new StringBuilder();

            int add_space = 0;

            for(int id = 1; id<=3; id++) {

                add_space = 0;

                for(int i = 0; i<given_string.length(); i++) {

                    if(curr_track[i] == id){

                        sb.append(given_string.charAt(i));
                        add_space = 1;

                    }

                }

                if(add_space == 1){

                    sb.append(' ');

                }
            }
            
            String str = sb.toString();
            str = str.trim();
            all_anagrams.add(str);

            return;
        }

        for(int i = 1; i<=3; i++){

            curr_track[idx] = i;
            generate_anagrams((idx + 1), strlen);
            
        }
    }
}

class Anagram{

	public static String sort_string1(String s){

        char c[] = s.toCharArray();
          
        Arrays.sort(c);

        String sorted_s = new String(c);

        return sorted_s;  
    }

    public static void main(String args[]) throws FileNotFoundException{

    	try{

	        Scanner scan1 = new Scanner(new File(args[0])); 
	        Scanner scan2 = new Scanner(new File(args[1])); 

	        int vocab_size = Integer.parseInt(scan1.nextLine());

	        int hash_table_size = 4*vocab_size;

	        hash_table hashtable = new hash_table(hash_table_size);

	        while(scan1.hasNextLine()){

	        	String hashed_s = scan1.nextLine();
	        	hashtable.insert(hashed_s);

	        }

	        int num_inputs = Integer.parseInt(scan2.nextLine());
	        
	        while(scan2.hasNextLine()){

	        	String input_s = scan2.nextLine();
	        	Vector<String> anagram_outputs = new Vector<String>();
	        	input_s = sort_string1(input_s);

	        	space_anagram_generator gen = new space_anagram_generator(input_s);
		        gen.generate_anagrams(0, input_s.length());
		        Collections.sort(gen.all_anagrams);
		        Vector<String> final_space_anagrams = new Vector<String>();

		        int llen = gen.all_anagrams.size();
		        for(int i = 0; i<llen; i++){

		            if((i + 1)<llen){

		                if(gen.all_anagrams.get(i).equals(gen.all_anagrams.get(i+1))){

		                    continue;

		                }
		            }

		            final_space_anagrams.add(gen.all_anagrams.get(i));
		        }

		        for(int i = 0; i<final_space_anagrams.size(); i++){

		        	String spaced_s = final_space_anagrams.get(i);
		        	String[] split_s = spaced_s.trim().split("\\s+");
		        	if(split_s.length == 1){

		        		String s1 = split_s[0];
		        		h_node input_node1 = hashtable.search(s1);
		        		if(input_node1!=null){

		        			for(int j = 0; j<input_node1.value.size(); j++){

		        				anagram_outputs.add(input_node1.value.get(j));
		        			}

		        		}


		        	} else if(split_s.length == 2){

		        		String s1 = split_s[0];
		        		String s2 = split_s[1];
		        		h_node input_node1 = hashtable.search(s1);
		        		h_node input_node2 = hashtable.search(s2);
		        		if(input_node1!=null && input_node2!=null){

		        			for(int j = 0; j<input_node1.value.size(); j++){

			        			for(int m = 0; m<input_node2.value.size(); m++){

			        				String s_new1 = input_node1.value.get(j) + " " + input_node2.value.get(m);
			        				// String s_new2 = input_node2.value.get(m) + " " + input_node1.value.get(j);
			        				anagram_outputs.add(s_new1);
			        				// anagram_outputs.add(s_new2);

			        			}

			        		}

		        		}
		        		

		        	} else if(split_s.length == 3){

		        		String s1 = split_s[0];
		        		String s2 = split_s[1];
		        		String s3 = split_s[2];
		        		h_node input_node1 = hashtable.search(s1);
		        		h_node input_node2 = hashtable.search(s2);
		        		h_node input_node3 = hashtable.search(s3);

		        		if(input_node1 == null || input_node2 == null || input_node3 == null){

		        			continue;

		        		}

		        		for(int j = 0; j<input_node1.value.size(); j++){

		        			for(int m = 0; m<input_node2.value.size(); m++){

		        				for(int u = 0; u<input_node3.value.size(); u++){

		        					String s_new1 = input_node1.value.get(j) + " " + input_node2.value.get(m) + " " + input_node3.value.get(u);
			        				// String s_new2 = input_node1.value.get(j) + " " + input_node3.value.get(u) + " " + input_node2.value.get(m);
			        				// String s_new3 = input_node2.value.get(m) + " " + input_node1.value.get(j) + " " + input_node3.value.get(u);
			        				// String s_new4 = input_node2.value.get(m) + " " + input_node3.value.get(u) + " " + input_node1.value.get(j);
			        				// String s_new5 = input_node3.value.get(u) + " " + input_node1.value.get(j) + " " + input_node2.value.get(m);
			        				// String s_new6 = input_node3.value.get(u) + " " + input_node2.value.get(m) + " " + input_node1.value.get(j);
			        				anagram_outputs.add(s_new1);
			        				// anagram_outputs.add(s_new2);
			        				// anagram_outputs.add(s_new3);
			        				// anagram_outputs.add(s_new4);
			        				// anagram_outputs.add(s_new5);
			        				// anagram_outputs.add(s_new6);

		        				}

		        			}

		        		}

		        	}

		        }
		        
		        if(anagram_outputs.size()>0){

		        	Collections.sort(anagram_outputs);
		        	for(int i = 0; i<anagram_outputs.size(); i++){

		        		System.out.println(anagram_outputs.get(i));

		        	}
		        	System.out.println(-1);

		        } else{

		        	System.out.println(-1);

		        }
		        
	        }

        } catch (FileNotFoundException e){

        	String ex = "";

        }

    }
}