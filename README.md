# README #

This README would normally document whatever steps are necessary to get your application up and running.

### Task #1 Execution

* Navigage to github\bash folder
*  bash Git.sh torvalds | jq -r '.[] | "\(.name) \(.stargazers_count)"'

Note : tried to get the release verison through below api. But it fails. validated with other git public repo, the count is not matching. 
https://api.github.com/repos/torvalds/pesconvert/releases/113099837



### Task #2 Execution
* Open the project in any ide 
	- Java 13 required
* mvn build to add the dependency to the project
* Execute the RunTest.java under testrunner folder.

