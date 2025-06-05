<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>README - Gymnasio Web Application</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #ffffff;
            color: #333;
        }
        h1, h2, h3 {
            color: #1f3c60;
        }
        code {
            background-color: #f2f2f2;
            padding: 2px 6px;
            border-radius: 4px;
            font-family: monospace;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        .section {
            margin-bottom: 40px;
        }
    </style>
</head>
<body>

    <h1>Gymnasio: Gym Management Platform</h1>
    <p><strong>Team:</strong> [Insert Team Name Here]</p>
    <p><strong>Contributors:</strong> Rishi Shah, Alvaro [Last Name], Jorge [Last Name], Mikel [Last Name]</p>

    <div class="section">
        <h2>Project Overview</h2>
        <p>Gymnasio is a full-stack web platform developed to support gym membership management, including features such as user authentication, profile management, class reservations, payment processing, and data analytics. The system enables members to interact with their fitness schedule while offering administrators tools for oversight and optimization.</p>
    </div>

    <div class="section">
        <h2>Technology Stack</h2>
        <ul>
            <li><strong>Frontend:</strong> HTML, CSS, JavaScript</li>
            <li><strong>Backend:</strong> Java, JSP, Servlets</li>
            <li><strong>Database:</strong> MySQL</li>
            <li><strong>Server:</strong> Apache Tomcat</li>
            <li><strong>Additional Technologies:</strong> AJAX, BCrypt for password hashing, Stripe Test API for payment simulation</li>
        </ul>
    </div>

    <div class="section">
        <h2>Features</h2>
        <ul>
            <li>Secure user registration, login, and password recovery with BCrypt encryption</li>
            <li>User profile management with real-time updates and image upload capability</li>
            <li>Class reservation system with dynamic availability and booking conflict prevention</li>
            <li>Simulated online payments using Stripe’s sandbox environment</li>
            <li>Account balance tracking with support for deposits, withdrawals, and transaction history</li>
            <li>Administrative dashboard with usage trends, booking statistics, and class popularity metrics</li>
        </ul>
    </div>

    <div class="section">
        <h2>Function Attribution</h2>
        <table>
            <thead>
                <tr>
                    <th>Function ID</th>
                    <th>Description</th>
                    <th>Developer</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>FR16–FR17</td><td>Simulated payment interface and validation</td><td>Rishi Shah</td></tr>
                <tr><td>FR18–FR22</td><td>Account balance management and transaction logging</td><td>Rishi Shah</td></tr>
                <tr><td>FR23–FR26</td><td>Client menu and accessibility features</td><td>Rishi Shah</td></tr>
                <tr><td>FR1–FR15</td><td>User authentication, profile handling, reservations</td><td>Alvaro, Jorge, Mikel</td></tr>
            </tbody>
        </table>
    </div>

    <div class="section">
        <h2>System Architecture</h2>
        <p>The application follows a modular architecture based on the Model-View-Controller (MVC) pattern. Servlets serve as controllers to process user input and manage communication between the frontend and backend logic. JavaScript and AJAX are used for dynamic client-side updates and data validation. The backend uses MySQL to persist membership data, reservations, and transaction records.</p>
    </div>

    <div class="section">
        <h2>Installation Instructions</h2>
        <ol>
            <li>Clone or download the repository into your Apache Tomcat <code>/webapps/</code> directory.</li>
            <li>Import the provided SQL schema into a local MySQL instance.</li>
            <li>Configure database credentials in <code>/WEB-INF/dbconfig.jsp</code>.</li>
            <li>Start Tomcat and access the application via <code>http://localhost:8080/Gymnasio/index.html</code>.</li>
        </ol>
    </div>

    <div class="section">
        <h2>Testing & Demonstration</h2>
        <p>The application includes full client-side validation and simulated payment testing using Stripe’s sandbox mode. Testing data and predefined test card numbers are supported within the interface to demonstrate various transaction scenarios (e.g., success, decline, insufficient funds).</p>
    </div>

    <div class="section">
        <h2>License & Usage Notice</h2>
        <p>This project is intended as a technical demonstration of full-stack web development capabilities. Payment processing is simulated through Stripe’s test environment and is not connected to any real financial service. The software is delivered as-is and is not intended for deployment in production environments without further security and scalability review.</p>
    </div>

</body>
</html>
