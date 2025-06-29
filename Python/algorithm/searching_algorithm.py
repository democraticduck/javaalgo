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


def binary_search_test(arr):
    import random, time

    bs = BinarySearch()

    n = len(arr) - 1

    # run 100 times to allocate memory first, so that later will be consistent
    for _ in range(100):
        target = random.choice(arr)
        bs.binarySearch(arr, 0, n, target)

    # Best Case
    mid = (0 + n) // 2
    target = arr[mid]
    start_time = time.perf_counter()
    bs.binarySearch(arr, 0, n, target)
    end_time = time.perf_counter()
    best_case_time = (end_time - start_time) * 1000

    # Average Case
    total_time = 0
    for _ in range(100):
        target = random.choice(arr)
        start_time = time.perf_counter()
        bs.binarySearch(arr, 0, n, target)
        end_time = time.perf_counter()
        total_time += (end_time - start_time) * 1000
    average_case_time = total_time / 100

    # Worst Case
    target = max(arr) + 10
    start_time = time.perf_counter()
    bs.binarySearch(arr, 0, n, target)
    end_time = time.perf_counter()
    worst_case_time = (end_time - start_time) * 1000

    return [best_case_time, average_case_time, worst_case_time]
