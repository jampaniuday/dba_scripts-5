ps -auxgw 2>/dev/null| sort -nrk 3| head -20| grep oracle | awk '{print $2 " " $3}'| while read a b
do
echo -e "\033[1;36m-------------------CPU Consumo $b -------------------\033[0m"
./"pid$1.sh" $a
done