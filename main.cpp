#include <vector>
#include <iostream>

using namespace std;

void swap(vector<int>& v, int i, int j) {
    int tmp = v[i];
    v[i] = v[j];
    v[j] = tmp;
}

void mergeSort(vector<int>& v, int i, int j) {
    if(i >= j) return;

    int mid = (i+j) / 2;

    int lp = i, rp = mid+1;
    while(lp <= mid && rp <= j) {
        if(v[lp] <= v[rp]) {
            lp++;
        }
        else {
            int val = v[lp];
            int oriRp = rp;
            do {
                swap(v, lp, rp);
                rp++;
                lp++;
            } while(lp <= mid && rp <= j && v[rp] < val);
            if(lp <= mid && rp <= j) {
                rp = oriRp;
            }
            else break;
        }
    }
    
}

int main() {

}