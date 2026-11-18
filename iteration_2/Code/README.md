## Sample data

- `data/availabilities.csv` – load with option 1
- `data/appointments.csv` – load with option 2, after option 1

## Layers

- `presentation` – `ConsoleUI` (menu); talks only to the controllers
- `domain` – controllers (`BookingController`, `AvailabilityController`, `DateController`), catalogs and domain classes
- `datasource` – `CsvGateway` (reads and writes CSV files)
