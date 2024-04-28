from gpiozero import Motor
from time import sleep

class VendingMachine:
    def __init__(self):
        self.items = {
            "ballpen": {"price": 30, "quantity": 10},
            "bondpaper": {"price": 50, "quantity": 10},
            "tape": {"price": 40, "quantity": 10}
        }
        self.balance = 0.00
        self.points = 0
        self.motor = Motor(forward=17, backward=18)  # Replace 17 and 18 with your actual GPIO pin numbers

    def insert_qr(self, amount):
        self.balance += amount

    def buy_item(self, item):
        if item not in self.items:
            print("Item not available")
            return

        if self.items[item]["quantity"] <= 0:
            print("Out of stock")
            return

        if self.balance < self.items[item]["price"]:
            print("Insufficient balance")
            return

        print(f"Dispensing {item}")
        self.items[item]["quantity"] -= 1
        self.balance -= self.items[item]["price"]
        self.points += 1  # Increment points for each purchase

    def check_balance(self):
        print(f"Your balance is ${self.balance:.2f}")

    def move_stepper_motor(self, direction, steps, delay):
        if direction == "forward":
            self.motor.forward()
        elif direction == "backward":
            self.motor.backward()
        else:
            print("Invalid direction")
            return

        for _ in range(steps):
            sleep(delay)
            self.motor.stop()
            sleep(delay)

    def check_points(self):
        print(f"You have {self.points} points")

    def display_items(self):
        print("Welcome to the Vending Machine!")
        print("Please select an item: 1, 2, or 3")
        
        for key, item in self.items.items():
            print(f"{key.capitalize()}. {key.capitalize()} - ${item['price']}")

    def process_selection(self):
        selection = input("Enter the item number you wish to purchase: ")

        if selection in self.items:
            selected_item = self.items[selection]
            print(f"You have selected {selection.capitalize()}.")
            amount_due = selected_item['price']

        while amount_due > 0:
            try:
                payment = float(input(f"Please insert ${amount_due:.2f}: "))
                if payment >= amount_due:
                    change = payment - amount_due
                    print(f"Thank you for your purchase! Your change is ${change:.2f}.")
                    break
                else:
                    print("Insufficient balance. Please insert more plastic bottles.")
                    amount_due -= payment
            except ValueError:
                print("Invalid QR. Please enter a valid QR Code.")
        else:
            print("Invalid selection. Please try again.")
