# Gymnasio: Gym Management Platform

**Contributors:** Rishi Shah, Alvaro Sola Gonzalvo , Jorge Calonge, Mikel 

---

## Project Overview

Gymnasio is a full-stack web platform developed to support gym membership management, including features such as user authentication, profile management, class reservations, payment processing, and data analytics. The system enables members to interact with their fitness schedule while offering administrators tools for oversight and optimization.

---

## Technology Stack

- **Frontend:** HTML, CSS, JavaScript  
- **Backend:** Java, JSP, Servlets  
- **Database:** MySQL  
- **Server:** Apache Tomcat  
- **Additional Technologies:** AJAX, BCrypt for password hashing, Stripe Test API for payment simulation

---

## Features

- Secure user registration, login, and password recovery with BCrypt encryption  
- User profile management with real-time updates and image upload capability  
- Class reservation system with dynamic availability and booking conflict prevention  
- Simulated online payments using Stripe’s sandbox environment  
- Account balance tracking with support for deposits, withdrawals, and transaction history  
- Administrative dashboard with usage trends, booking statistics, and class popularity metrics

---

## Function Attribution

| Function ID | Description                                 | Developer       |
|-------------|---------------------------------------------|-----------------|
| FR16–FR17   | Simulated payment interface and validation  | Rishi Shah      |
| FR18–FR22   | Account balance management and transaction logging | Rishi Shah |
| FR23–FR26   | Client menu and accessibility features      | Rishi Shah      |
| FR1–FR15    | User authentication, profile handling, reservations | Alvaro, Jorge, Mikel |

---

## System Architecture

The application follows a modular architecture based on the Model-View-Controller (MVC) pattern. Servlets serve as controllers to process user input and manage communication between the frontend and backend logic. JavaScript and AJAX are used for dynamic client-side updates and data validation. The backend uses MySQL to persist membership data, reservations, and transaction records.

---

## Installation Instructions

1. Clone or download the repository into your Apache Tomcat `/webapps/` directory.  
2. Import the provided SQL schema into a local MySQL instance.  
3. Configure database credentials in `/WEB-INF/dbconfig.jsp`.  
4. Start Tomcat and access the application via:  
   `http://localhost:8080/Gymnasio/index.html`

---

## Testing & Demonstration

The application includes full client-side validation and simulated payment testing using Stripe’s sandbox mode. Testing data and predefined test card numbers are supported within the interface to demonstrate various transaction scenarios (e.g., success, decline, insufficient funds).

---

## License & Usage Notice

This project is intended as a technical demonstration of full-stack web development capabilities. Payment processing is simulated through Stripe’s test environment and is not connected to any real financial service. The software is delivered as-is and is not intended for deployment in production environments without further security and scalability review.
