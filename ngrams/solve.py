# input=raw_input("Input file name: ")
# N=raw_input("Value of N: ")
import random
K=(int)(raw_input("# of random words to generate: "))

def read_process_data():
    with open("mobydick.txt") as f:
        content = ' '.join(f.readlines()).replace('\n','').replace('\r','').lower()
        return content

content=' '+read_process_data()
sp=content.find(' ',60)
content+=content[0:sp]
seed="moby dick "   #what I set it as
# print(content)
def getNextWord():
    if(seed==" " or seed==""):
        print("Bad seed")
    dict={}
    start=0
    pos=0
    total=0
    while(True):
        pos=content.find(seed, start)
        if pos==-1:
            break
        nextSpace=content.find(' ',pos+len(seed))
        # if(nextSpace==start):
        #     print("bad seed: "+seed)
        #     return "BAD"
        nextWord=content[pos+len(seed):nextSpace]
        start=nextSpace
        # if len(nextWord)< 2:
        #     continue
        # print(str(pos)+" "+str(nextSpace))
        dict[nextWord]=(dict[nextWord] if nextWord in dict else 0) +1
        total+=1

    # print(dict)
    max=0
    toAdd=""
    rand=random.randint(0,total)
    print("Total: "+str(total))
    curr=0
    for word in dict:   #make it weighted random!
        curr+=dict[word]
        if curr>=rand:
            return word
    # print(seed+toAdd+" "+str(dict[toAdd]))
res=seed
for i in range(2,K):
    nextWord=getNextWord()+' '
    seed= seed[seed.find(' ')+1:]+nextWord
    print("Seed: "+seed)
    res+=nextWord
print(res)
