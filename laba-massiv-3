void main() {
    int[] mas = {21, 10, 11, 5, -3, 8, 1, -14, -2, -8};
    System.out.println("Do");
    System.out.println(Arrays.toString(mas));
    quickSort(mas,0,3);
    quickSort2(mas,6,9);
    System.out.println("Posle");
    System.out.println(Arrays.toString(mas));
}
void Sort(int [] array,int x,int y)
{
    if (array.length == 0) return;
    if(x >= y) return;
    int sered = x + (y - x)/2;
    int opora = array [sered];
    int i = x , j = y;
    while ( i<=j){
        while (array[i]<opora){
            i++;
        }
        while(array[j]>opora){
            j--;
        }if ( i <= j)
        {
            int temp = array[i];
            array[i]= array[j];
            array [j]= temp;
            i++;
            j--;
        }
    }
    if (x<j){
        Sort(array,x,j);
    }
    if (y>i){
        Sort(array, i,y);
    }
}
void Sort2(int [] array, int x, int y) {
    if (array.length == 0) return;
    if(x >= y) return;
    int sered = x + (y - x)/2;
    int opora = array[sered];
    int i = x, j = y;
    while (i <= j) {
        while (array[i] > opora) i++;
        while (array[j] < opora) j--;
        if (i <= j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }
    }
    if (x < j) Sort2(array, x, j);
    if (y > i) Sort2(array, i, y);
}
