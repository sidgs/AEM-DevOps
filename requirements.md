# Check if Bundle Present 
Create a shell script that will check if a bundle is present in AEM. This shell script will take as input the following 
1. symbolic name 
2. version ( optional ) 
3. AEM Host Name 
4. AEM Port 
5. http/https 

Output : 
Format : JSON 
- if Present : {"count" : X 
"instances" : [ {"version": " ", "state" : "" },{"version": " ", "state" : ""}]
}
- if Not Present : {"count": 0 } 
