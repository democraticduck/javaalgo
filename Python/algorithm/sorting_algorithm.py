# Merge Sort
# Reference: https://www.geeksforgeeks.org/dsa/merge-sort/
class MergeSort:
    def merge(self, arr, left, mid, right):
        n1 = mid - left + 1
        n2 = right - mid

        # Create temp arrays
        L = [0] * n1
        R = [0] * n2

        # Copy data to temp arrays L[] and R[]
        for i in range(n1):
            L[i] = arr[left + i]
        for j in range(n2):
            R[j] = arr[mid + 1 + j]

        i = 0  # Initial index of first subarray
        j = 0  # Initial index of second subarray
        k = left  # Initial index of merged subarray

        # Merge the temp arrays back
        # into arr[left..right]
        while i < n1 and j < n2:
            if L[i] <= R[j]:
                arr[k] = L[i]
                i += 1
            else:
                arr[k] = R[j]
                j += 1
            k += 1

        # Copy the remaining elements of L[],
        # if there are any
        while i < n1:
            arr[k] = L[i]
            i += 1
            k += 1

        # Copy the remaining elements of R[], 
        # if there are any
        while j < n2:
            arr[k] = R[j]
            j += 1
            k += 1

    def mergeSort(self, arr, left, right):
        if left < right:
            mid = (left + right) // 2

            self.mergeSort(arr, left, mid)
            self.mergeSort(arr, mid + 1, right)
            self.merge(arr, left, mid, right)

# Merge Sort Step
# Reference: https://www.geeksforgeeks.org/dsa/merge-sort/
class MergeSortStep:
    def __init__(self):
        self.step = []

    def merge(self, arr, left, mid, right):
        n1 = mid - left + 1
        n2 = right - mid

        # Create temp arrays
        L = [0] * n1
        R = [0] * n2

        # Copy data to temp arrays L[] and R[]
        for i in range(n1):
            L[i] = arr[left + i]
        for j in range(n2):
            R[j] = arr[mid + 1 + j]

        i = 0  # Initial index of first subarray
        j = 0  # Initial index of second subarray
        k = left  # Initial index of merged subarray

        # Merge the temp arrays back
        # into arr[left..right]
        while i < n1 and j < n2:
            if L[i] <= R[j]:
                arr[k] = L[i]
                i += 1
            else:
                arr[k] = R[j]
                j += 1
            k += 1

        # Copy the remaining elements of L[],
        # if there are any
        while i < n1:
            arr[k] = L[i]
            i += 1
            k += 1

        # Copy the remaining elements of R[], 
        # if there are any
        while j < n2:
            arr[k] = R[j]
            j += 1
            k += 1

        self.step.append(arr[:])

    def mergeSort(self, arr, left, right):
        if left < right:
            mid = (left + right) // 2

            self.mergeSort(arr, left, mid)
            self.mergeSort(arr, mid + 1, right)
            self.merge(arr, left, mid, right)

    def execute(self, arr, left, right):
        self.step = []
        self.step.append(arr[:])
        self.mergeSort(arr, left, right)
        return self.step

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

    def execute(self, S, left, right):
        self.step = []
        self.step.append(S[:])
        self.quickSort(S, left, right)
        return self.step
