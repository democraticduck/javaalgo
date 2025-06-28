# Binary Search
# Reference: https://www.geeksforgeeks.org/dsa/binary-search/
class BinarySearch:
    def binarySearch(self, arr, low, high, x):

        while low <= high:

            mid = low + (high - low) // 2

            # Check if x is present at mid
            if arr[mid] == x:
                return mid

            # If x is greater, ignore left half
            elif arr[mid] < x:
                low = mid + 1

            # If x is smaller, ignore right half
            else:
                high = mid - 1

        # If we reach here, then the element
        # was not present
        return -1

# Binary Search Step
# Reference: https://www.geeksforgeeks.org/dsa/binary-search/
class BinarySearchStep:
    def __init__(self):
        self.step = []

    def binarySearch(self, arr, low, high, x):

        while low <= high:

            mid = low + (high - low) // 2
            self.step.append(mid)

            # Check if x is present at mid
            if arr[mid] == x:
                return mid

            # If x is greater, ignore left half
            elif arr[mid] < x:
                low = mid + 1

            # If x is smaller, ignore right half
            else:
                high = mid - 1

        # If we reach here, then the element was not present
        self.step.append(-1)
        return -1

    def execute(self, arr, low, high, x):
        self.binarySearch(arr, low, high, x)
        return self.step
