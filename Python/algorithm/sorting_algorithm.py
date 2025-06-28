# Merge Sort
class MergeSort:
    def merge(self, S, left, mid, right):
        L = S[left : mid + 1]
        R = S[mid + 1 : right + 1]
        k = left
        
        while L and R:
            if L[0] < R[0]:
                S[k] = L.pop(0)
            else:
                S[k] = R.pop(0)
            k += 1

        while L:
            S[k] = L.pop(0)
            k += 1

        while R:
            S[k] = R.pop(0)
            k += 1

    def mergeSort(self, S, left, right):
        if left < right:
            mid = (left + right) // 2
            self.mergeSort(S, left, mid)
            self.mergeSort(S, mid + 1, right)
            self.merge(S, left, mid, right)
        return S

# Merge Sort Step
class MergeSortStep:
    def __init__(self):
        self.step = []

    def merge(self, S, left, mid, right):
        L = S[left : mid + 1]
        R = S[mid + 1 : right + 1]
        k = left
        
        while L and R:
            if L[0] < R[0]:
                S[k] = L.pop(0)
            else:
                S[k] = R.pop(0)
            k += 1

        while L:
            S[k] = L.pop(0)
            k += 1

        while R:
            S[k] = R.pop(0)
            k += 1

        self.step.append(S[:])

    def mergeSort(self, S, left, right):
        if left < right:
            mid = (left + right) // 2
            self.mergeSort(S, left, mid)
            self.mergeSort(S, mid + 1, right)
            self.merge(S, left, mid, right)
        return S

    def execute(self, S, left, right):
        self.step = []
        self.step.append(S[:])
        self.mergeSort(S, left, right)
        return self.step

    def output():
        pass

# Quick Sort
# Reference: https://www.geeksforgeeks.org/dsa/python-program-for-quicksort/
class QuickSort:
    def partition(self, S, left, right):

        # choose the rightmost element as pivot
        pivot = S[right]

        # pointer for greater element
        i = left - 1

        for j in range(left, right):
            if S[j] <= pivot:
                i = i + 1
                (S[i], S[j]) = (S[j], S[i])

        pi = i + 1 # pivot index
        (S[pi], S[right]) = (S[right], S[pi])
        return pi

    def quickSort(self, S, left, right):
        if left < right:
            pi = self.partition(S, left, right)
            self.quickSort(S, left, pi - 1)
            self.quickSort(S, pi + 1, right)
        return S

# Quick Sort Step
# Reference: https://www.geeksforgeeks.org/dsa/python-program-for-quicksort/
class QuickSortStep:
    def __init__(self):
        self.step = []

    def partition(self, S, left, right):

        # choose the rightmost element as pivot
        pivot = S[right]

        # pointer for greater element
        i = left - 1

        for j in range(left, right):
            if S[j] <= pivot:
                i = i + 1
                (S[i], S[j]) = (S[j], S[i])

        pi = i + 1 # pivot index
        (S[pi], S[right]) = (S[right], S[pi])
        self.step.append((pi, S[:]))
        return pi

    def quickSort(self, S, left, right):
        if left < right:
            pi = self.partition(S, left, right)
            self.quickSort(S, left, pi - 1)
            self.quickSort(S, pi + 1, right)
        return S

    def execute(self, S, left, right):
        self.step = []
        self.step.append(S[:])
        self.quickSort(S, left, right)
        return self.step
