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
    from datetime import datetime
    import random, time

    bs = BinarySearch()

    size = len(arr)
    n = size - 1

    # run 100 times to allocate memory first, so that later will be consistent
    for _ in range(100):
        target = random.choice(arr)
        bs.binarySearch(arr, 0, n, target)

    total_time = 0
    lowest_time = float('inf')
    highest_time = -1

    print("Start time:", datetime.now().strftime("%Y-%m-%d %H:%M:%S.%f")[:-3])

    for target in arr:
        start_time = time.perf_counter()
        bs.binarySearch(arr, 0, n, target)
        end_time = time.perf_counter()
        execution_time = ((end_time - start_time) * 1000)
        if execution_time < lowest_time:
            lowest_time = execution_time
        if execution_time > highest_time:
            highest_time = execution_time
        total_time += execution_time

    print("End time  :", datetime.now().strftime("%Y-%m-%d %H:%M:%S.%f")[:-3])

    best_case_time = lowest_time
    worst_case_time = highest_time
    average_case_time = total_time / size

    return [best_case_time, average_case_time, worst_case_time]
