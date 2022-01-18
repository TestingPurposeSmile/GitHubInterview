#!/bin/bash

LoginName=$(curl -s 'https://api.github.com/users/'$1 | jq -r '.login')
echo "Login Name is :	"$LoginName 
Name=$(curl -s 'https://api.github.com/users/'$1 | jq -r '.name')
echo "Name is :	"$Name
CreatedAt=$(curl -s 'https://api.github.com/users/'$1 | jq -r '.created_at')
echo "CreatedAt " $CreatedAt 


RepoName=$(curl -sS "https://api.github.com/users/$1/repos" | jq -r '.[]| "\(.name)\t "')
startCount=$(curl -sS "https://api.github.com/users/$1/repos" | jq -r '.[].stargazers_count')

array=(${RepoName})

array1=($startCount)

for i in "${!array[@]}"
do
	countOfrepo=$(curl -sS "https://api.github.com/repos/$1/${array[i]}/releases" | jq '. | length')
	echo "Name of the repository  is: "${array[i]}
	echo "		Star = " ${array1[i]}
	echo "		Release = "$countOfrepo

done