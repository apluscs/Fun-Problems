/* "aabbbbcccd"
“”
“a”
“aadddd”
“aaaadd” */

// returns longest substring of repeated characters in str
string solve(string str){
    string res=””;
    int N=str.length();
    for(int i=0;i!=N;i++){
        char c=str.charAt(i);
        int j=i+1;
        while(j!=N && str.chatAt(j) == c)){
            j++;   
        }
        if(j-i > res.length()) res=str.substring(i,j);
        i=j-1;
    }
    return res;
}

/* 012 → 020
222 1000
0
1
2
20
21
212
3
-1
-10 → -2 */

// Takes a string of base 3, returns that string + 1 in base 3
string solve2 (string str){
    string res= ””;
    if(str.length()==0) //throw an exception
    int i=str.length()-1;
    while(i!=-1 && str.charAt(i) == ‘2’){
        res=’0’+res;
        i--;
    }
    if(i==-1) res=’1’+res;
    else{
        res= (char)(str.chatAt(i)+1)+res;
        res= res.substring(0,i)+res;
    }
    return res;
}
