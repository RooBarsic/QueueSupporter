
public class Numbers {
    public int summ(int a, int b){
        return a + b;
    }

    public int intervalSumm(int a, int b){
        int resultSumm = 0;
        for(int i = a; i <= b; i++){
            resultSumm += i;
        }
        return resultSumm;
    }
}
