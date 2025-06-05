// Utility: extract clientId from URL
function getClientIdFromURL() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get("clientId");
}

const clientId = getClientIdFromURL();
if (!clientId) {
    alert("Client ID missing in URL.");
}

// Select service row
function selectService(row, serviceId) {
    const allRows = document.querySelectorAll("tr[data-service-id]");
    allRows.forEach(r => r.classList.remove("selected"));
    row.classList.add("selected");
    document.getElementById("selectedServiceID").value = serviceId;
    document.getElementById("dateAndTimeForm").classList.remove("hidden");
}

// Event listener: Pay with Card
const payWithCardButton = document.getElementById("payWithCardButton");
if (payWithCardButton) {
    payWithCardButton.addEventListener("click", () => {
        const serviceId = document.getElementById("selectedServiceID").value;
        const date = document.getElementById("selectedDate").value;
        const time = document.getElementById("selectedTime").value;

        if (!serviceId || !date || !time) {
            alert("Please select a service, date, and time.");
            return;
        }

        const query = new URLSearchParams({
            clientId,
            serviceId,
            selectedDate: date,
            selectedTime: time
        });

        window.location.href = "PaymentCard?" + query.toString();
    });
}

// Event listener: Pay at the Gym
const payAtGymButton = document.getElementById("payAtGymButton");
if (payAtGymButton) {
    payAtGymButton.addEventListener("click", () => {
        const serviceId = document.getElementById("selectedServiceID").value;
        const date = document.getElementById("selectedDate").value;
        const time = document.getElementById("selectedTime").value;

        if (!serviceId || !date || !time) {
            alert("Please select a service, date, and time.");
            return;
        }

        const query = new URLSearchParams({
            clientId,
            serviceId,
            selectedDate: date,
            selectedTime: time
        });

        window.location.href = "AddReservationUnPaid?" + query.toString();
    });
}
