import time
#6.88 sec for "barbara bush" = must faster = USE THIS!!
def read_process_data():
    with open('dictionary.txt') as f:
        content = ' '.join(f.readlines()).replace('\r','').replace('\n','')
        return content

def usedAll(have):  #if we used all the characters in key, anagram is complete
    for frq in have:
        if frq !=0:
            return False
    return True

def getDictFrq(dict,dictFrq):   #creates a frequency of letters of each word in dictionary
    for i in range(len(dict)):
        word=dict[i]
        frq=[0]*26
        for c in word:
            if c==' ':
                continue
            frq[ord(c)-ord('a')]+=1
        dictFrq[i]=frq

def helper(have,dict,currAnagram,res,dictFrq):
    if usedAll(have):
        # print(currAnagram)
        res.append(currAnagram[:])  #cloning needed?
        return
    for i in range (len(dict)):
        wordFrq=dictFrq[i]
        flag=False  #tracks if word is not able to be made
        for j in range(26):
            if have[j]<wordFrq[j]:
                flag=True
                break
        if flag:
            continue
        for j in range(26): #subtract letters we're using from what we have
            have[j]-=wordFrq[j]
        word=dict[i]    #word corresponding to dictFrq[i]
        # print(word)
        currAnagram.append(word)
        helper(have,dict,currAnagram,res,dictFrq)
        currAnagram.remove(word)
        for j in range(26): #add letters back in to prepare for next word to try
            have[j]+=wordFrq[j]

def filter(dictFrq,keyFrqs,wholeDict):
    newDictFrq=[]
    newDict=[]
    for j in range(len(dictFrq)):
        wordFrq=dictFrq[j]
        flag=False
        for i in range(26):
            if(keyFrqs[i]<wordFrq[i]):
                flag=True
                break
        if(flag):
            continue
        newDict.append(wholeDict[j])
        newDictFrq.append(dictFrq[j])
    return [newDict,newDictFrq]

def main():
    startTime=time.time()
    # S=raw_input("What word do you want to anagram? ")
    S=raw_input("Enter a word: ")
    S=S.lower().replace(' ','')
    keyFrqs=[0]*26
    for c in S:
        if(c==' '):
            continue
        keyFrqs[ord(c)-ord('a')]+=1
    dict=read_process_data().split()    #array of words in dictionary
    # print(content)
    res=[]
    currAnagram=[]
    dictFrq=[None]*len(dict)
    getDictFrq(dict,dictFrq)
    temp=filter(dictFrq,keyFrqs,dict) #filter dictionary to use only words that will fit in S
    dict=temp[0]
    dictFrq=temp[1]
    # print(dict)
    helper(keyFrqs,dict,currAnagram,res,dictFrq)
    print (res)
    print("Those are all of the anagrams you could make with your word. Enjoy!")
    endTime=time.time()
    print("Time elapsed: "+str(endTime-startTime))

main()
