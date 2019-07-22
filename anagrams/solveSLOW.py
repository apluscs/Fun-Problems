import time #SLOW AF, 146 sec for 'barbarabush'
biglist=[]


def isPrefix(prefix, dict):    #if any word in dict starts with prefix
    st=0
    end=len(dict)-1
    while st<=end:  #binary search
        mid=(st+end)/2
        curr=dict[mid]
        if len(prefix)<=len(curr) and curr[:len(prefix)]==prefix:
            return True
        elif prefix < curr:
            end=mid-1
        else:
            st=mid+1
    return False

def findAna(commit,prefix,suffix,dict):
    global biglist
    # print(prefix+" "+suffix)
    if len(prefix)==0 and len(suffix)==0:
        # print(commit)
        if commit not in biglist:
            biglist.append(commit[:])
        return
    if not isPrefix(prefix,dict):
        return
    if prefix in dict:  #prefix is an actual word, try adding it to commit and set prefix to empty string
        # print("Found!")
        findAna(commit+[prefix],"",suffix,dict)
    for i in range(len(suffix)):    #try moving each char in suffix to prefix
        findAna(commit,prefix+suffix[i],suffix[:i]+suffix[i+1:],dict)
def read_process_data():
    with open('dictionary.txt') as f:
        content = ' '.join(f.readlines()).replace('\n','').replace('\r','').lower()
        return content.split()

def filter(content,key):
    newDict=[]
    keyFrqs=[0]*26
    for j in range(len(key)):
        c=ord(key[j])-ord('a')
        keyFrqs[c]+=1
    for j in range(len(content)):
        word=content[j]
        wordFrq=[0]*26
        for i in range(len(word)):
            c=ord(word[i])-ord('a')
            wordFrq[c]+=1
        flag=False
        for i in range(26):
            if(keyFrqs[i]<wordFrq[i]):
                flag=True
                break
        if(flag):
            continue
        newDict.append(word)
    return newDict

def main():
    startTime=time.time()
    key =''.join(sorted(raw_input("Enter a word: ").lower().replace(' ','')))
    print(key)
    dict=read_process_data()  #array
    dict=filter(dict,key)   #only words that can fit into key, so much faster!
    # print(dict)
    i=0
    N=len(key)
    while(i!=N):    #start only with unique letters in key as prefix, no point in doing 'a' and 'a' twice
        print(key[i])
        findAna([],key[i],key[:i]+key[i+1:],dict)
        while(i!=N-1 and key[i+1]==key[i]):
            i+=1
        i+=1
    print(biglist)  #answer
    endTime=time.time()
    print("Time elapsed: "+str(endTime-startTime))
main()
