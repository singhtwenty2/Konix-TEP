# Konix - Trade Execution Platform

Welcome to the Konix trade execution platform repository! 🎉

## Overview
Konix is a full-stack application developed to provide users with a realistic stock market trading experience without the need to invest real money. The platform allows users to engage in paper trading, helping them learn and understand how the stock market works.

## Tech Stack
- **Backend**: [![Ktor](https://img.shields.io/badge/Backend-Ktor-blue)](https://ktor.io/)
- **Database**: [![MySQL](https://img.shields.io/badge/Database-MySQL-blue)](https://www.mysql.com/)
- **Authentication**: [![JWT](https://img.shields.io/badge/Authentication-JWT-yellow)](https://jwt.io/)
- **Email Client**: [![SendGrid](https://img.shields.io/badge/Email%20Client-SendGrid-lightblue)](https://sendgrid.com/)
- **Android App**: [![Kotlin](https://img.shields.io/badge/Android%20App-Kotlin-green)](https://kotlinlang.org/)

## Features
- **Paper Trading**: Allows users to simulate stock trading without real money, promoting learning and experience.
- **Real-Time Graphs**: Displays cumulative and real-time stock price graphs for various companies.
- **Artificial Supply-Demand**: Simulates stock price movements based on artificial supply and demand models.
- **Authentication**: Secure authentication using JWT tokens.
- **Email Verification**: OTP verification via email using SendGrid.

## Project Details
The Konix platform is designed to offer users an educational and risk-free experience of stock market trading. By simulating real-time market conditions and providing tools such as price graphs and supply-demand models, users can practice and learn about trading strategies, market behaviors, and investment decision-making.

## Android App Demo

### Login Screen
<img src="ASSESTS/login_screen.png" alt="Login Screen" width="300">

### Signup Screen
<img src="ASSESTS/signup_screen.png" alt="Dashboard Screen" width="300">

### Home Screen
<img src="ASSESTS/home_screen.png" alt="Trading Screen" width="300">

### Profile Screen
<img src="ASSESTS/profile_screen.png" alt="Portfolio Screen" width="300">

### Portfolio Screen
<img src="ASSESTS/portfolio_screen.png" alt="Portfolio Screen" width="300">

### Company Detail Screen
<img src="ASSESTS/company_detail.png" alt="Portfolio Screen" width="300">

### Cummlative Stock Chart Screen
<img src="ASSESTS/cm_chart.png" alt="Portfolio Screen" width="300">

### Realtime Stock Chart Screen
<img src="ASSESTS/rt_chart.png" alt="Portfolio Screen" width="300">

### Buy Limit Order Screen
<img src="ASSESTS/limit_type_buy.png" alt="Portfolio Screen" width="300">

### Buy Market Order Screen
<img src="ASSESTS/market_type_buy.png" alt="Portfolio Screen" width="300">

### Sell Limit Order Screen
<img src="ASSESTS/limit_type_sell.png" alt="Portfolio Screen" width="300">

### Order History Screen
<img src="ASSESTS/order_history_order_feature.png" alt="Portfolio Screen" width="300">

### NSE Detail Screen Screen
<img src="ASSESTS/nse_detail.png" alt="Portfolio Screen" width="300">

### BSE Detail Screen Screen
<img src="ASSESTS/bse_details.png" alt="Portfolio Screen" width="300">

## Setup
1. **Backend (Ktor)**:
   - Clone the repository.
   - Navigate to the `backend` directory.
   - Install dependencies: 
     ```bash
     ./gradlew build
     ```
   - Run the server: 
     ```bash
     ./gradlew run
     ```

2. **Database (MySQL)**:
   - Ensure MySQL is installed and running.
   - Create a new database or use an existing one.
   - Import the database schema provided in the `database` directory.

3. **Android App**:
   - Open the project in Android Studio.
   - Build and run the app on an emulator or physical device.

## License
This project is licensed under the [MIT License](LICENSE).

Feel free to contribute and improve this project. Happy coding! 🚀
