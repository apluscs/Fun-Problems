import random
dir=[[0,1],[0,-1],[1,0],[-1,0]]

def makeGrid(grid,N):   #grid of random characters
    for i in range(N):
        for j in range(N):
            c=chr(random.randint(0,25)+ord('a'))
            grid[i][j]=c

def findWord(ind,r,c,key,N,vis,grid,res):
    if ind==len(key):   #reached end of word
        if key not in res:
            res.append(key)
        return True
    if r==N or r==-1 or c==N or c==-1 or vis[r][c] or grid[r][c] !=key[ind]:
        return False
    vis[r][c]=True  #only executes if grid[r][c] is the char we're looking for
    ind+=1
    for d in dir:   #expand NSEW to adjacent cells
        i=r+d[0]
        j=c+d[1]
        findWord(ind,i,j,key,N,vis,grid,res)
    vis[r][c]=False
    return False

def read_process_data():
    with open('dict2.txt') as f:
        # the following line
        # - joins each line in the file into one big string
        # - removes all newlines and carriage returns
        # - converts everything to lowercase
        content = ' '.join(f.readlines()).replace('\n','').replace('\r','').lower()
        return content.split()  #one array of words

def main():
    dict=read_process_data()
    N=int(raw_input("What size grid do you want? "))
    grid=[None]*N
    res=[]
    for i in range (N):
        grid[i]=['.']*N
    makeGrid(grid,N)
    for key in dict:
        vis=[None]*N
        for i in range(N):
            vis[i]=[False]*N
        for r in range(N):
            for c in range(N):
                findWord(0,r,c,key,N,vis,grid,res) #try all starting values
    for i in range(N):
        print(grid[i])
    print(res)
main()
