from datetime import datetime

import csv, random, string, time

def generate_dataset(n, filename):
    used_numbers = set()
    data = []

    print("Start time:", datetime.now().strftime("%Y-%m-%d %H:%M:%S.%f")[:-3])  # HH:MM:SS.mmm
    start_time = time.time()

    while len(data) < n:
        number = random.randint(1, 1_000_000_000)
        if number not in used_numbers:
            used_numbers.add(number)
            word = ''.join(random.choices(string.ascii_lowercase, k=5))
            data.append([number, word])

    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerows(data)

    print(f"{n} rows written to {filename}")

    end_time = time.time()
    print("End time  :", datetime.now().strftime("%Y-%m-%d %H:%M:%S.%f")[:-3])
    print(f"Duration  : {end_time - start_time:.3f} seconds")
    print(f"{n} rows written to {filename}")


if __name__ == "__main__":
    print("=== Dataset Generator ===")
    try:
        size = int(input("Enter number of rows to generate: "))
        filename = f"dataset_{size}.csv"
        generate_dataset(size, filename)
    except ValueError:
        print("Please enter a valid number.")
