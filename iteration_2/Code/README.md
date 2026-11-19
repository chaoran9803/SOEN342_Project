# Appointment Tracking System – Iteration II PoC

## Requirements

- JDK 11 or later

## Compile and run

Run these commands from the `Code` folder (the sample files are read from `data/` relative to it):

```
javac -d out -sourcepath src src/Main.java
java -cp out Main
```

In Eclipse, import `Code` as an existing project and run `Main`. Set the working directory to the `Code` folder.

## Quick start

The system starts empty: no professionals or slots exist until you import them.

1. Choose **1** and enter `data/availabilities.csv` to load the professionals and their slots.
2. (Optional) Choose **2** and enter `data/appointments.csv` to load the booked appointments. This must be done after step 1.
3. You can now use the other options.

## Menu

| Option | Feature |
|---|---|
| 1 | Import availabilities from CSV |
| 2 | Import booked appointments from CSV |
| 3 | Export all availabilities to CSV (e.g. `data/export.csv`) |
| 4 | Search availability by professional and date range (leave blank for all) |
| 5 | Make availability for a professional: date range, repetition pattern (days of the week, e.g. `MON,WED`, or a day of the month) and start/end time |
| 6 | Book an appointment: search or register the patient, pick a slot and a service |
| 7 | Create a referral (needed before booking *Specialist consultation*) |
| 8 | View appointments |
| 0 | Exit |

Dates use `YYYY-MM-DD` and times use `HH:MM`.

## Sample data

- `data/availabilities.csv` – load with option 1
- `data/appointments.csv` – load with option 2, after option 1

## Layers

- `presentation` – `ConsoleUI` (menu); talks only to the controllers
- `domain` – controllers (`BookingController`, `AvailabilityController`, `DateController`), catalogs and domain classes
- `datasource` – `CsvGateway` (reads and writes CSV files)
