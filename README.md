**This repository includes parsing logic of n number of String elements via performing request from remote server:**

  * if it is in correct format type of URL,
  * if it has a domain and accessible website, 
  * if it is in the correct date and words are integer format

**CSV Content:** 

Speaker;Topic;Date;Words  
Noam Chomsky; education policty; 2013-10-30; 5310  
Bernhard Belling; coal subsidies; 2012-11-05; 1210  
Caesare Collins; coal subsidies; 2012-11-06; 1119  
Alexander Abel; homeland security; 2012-12-11; 911  

**Questions are answered via parsing and control logic mentioned above and return JSON response:** 

_1) Which politician gave the most speeches in 2013?_  
_2) Which politician gave the most speeches on "homeland security"?_  
_3) Which politician spoke the fewest words overall?_  


**JSON response:** 

`{  
    "mostSpeeches": "Noam Chomsky",
    "mostSecurity": "Alexander Abel",
    "leastWordy": "Caesare Collins"
}`
