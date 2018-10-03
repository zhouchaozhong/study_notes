常见的几种排序算法
=============================================================================

* 插入排序

```   
    //插入排序
    void insertSort(int arr[],int len) {
        int j;
        for (int i = 1; i < len;i++) {
            if (arr[i] < arr[i-1]) {
                int temp = arr[i];
                for (j = i - 1; j >= 0 && temp < arr[j];j--) {
                    arr[j + 1] = arr[j];
                }

                arr[j+1] = temp;
            }

        }
    }

```

* 希尔排序

```
    //希尔排序
    void shellSort(int arr[],int len) {
        int increasement = len;
        int i,j,k;
        do {
            increasement = increasement / 3 + 1;
            for (i = 0; i < increasement;i++) {

                for (j = i + increasement; j < len;j+=increasement) {

                    if (arr[j] < arr[j - increasement]) {
                        
                        int temp = arr[j];

                        for (k = j - increasement; k >= 0 && temp < arr[k];k -= increasement) {
                        
                            arr[k + increasement] = arr[k];
                        }

                        arr[k + increasement] = temp;
                    }
                }
            }
        } while (increasement > 1);
    }

```

* 归并排序

```
    //合并有序序列
    void Merge(int arr[], int start, int end,int mid, int * temp) {
        int i_start = start;
        int i_end = mid;
        int j_start = mid + 1;
        int j_end = end;

        int len = 0; //表示辅助空间中有多少个元素
        while(i_start <= i_end && j_start <= j_end) {
            if (arr[i_start] < arr[j_start]) {
                temp[len] = arr[i_start];
                len++;
                i_start++;
            }
            else {
                temp[len] = arr[j_start];
                len++;
                j_start++;
            }
        }

        while (i_start <= i_end) {
            temp[len] = arr[i_start];
            len++;
            i_start++;
        }

        while (j_start <= j_end) {
            temp[len] = arr[j_start];
            len++;
            j_start++;
        }

        for (int i = 0; i < len;i++) {
            arr[start + i] = temp[i];
        }
        
    }


    //归并排序
    void mergeSort(int arr[],int start,int end,int * temp) {

        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        mergeSort(arr,start,mid,temp);
        mergeSort(arr,mid+1,end,temp);
        Merge(arr,start,end,mid,temp);
    }

    int main()
    {
        int arr[6] = {2,1,0,5,4,3};
        int i;

        int * temp = (int *)malloc(sizeof(int)*6);
        
        mergeSort(arr,0,5,temp);
        for (i = 0; i < 6;i++) {
            printf("%d ",arr[i]);
        }

        printf("\n");

        system("pause");
        return 0;
    }


```

