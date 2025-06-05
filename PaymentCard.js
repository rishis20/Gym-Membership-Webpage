window.onload = () => {
    const form = document.getElementById("paymentForm");
    const cardNumberInput = document.getElementById("cardNumber");
    const expiryDateInput = document.getElementById("expiryDate");
    const cvvInput = document.getElementById("cvv");

    // Handle both possible IDs for error display
    const errorContainer = document.getElementById("errorMsg") || document.getElementById("errorMessage");

    // Set min expiry to next month
    const today = new Date();
    const nextMonth = new Date(today.getFullYear(), today.getMonth() + 1, 1);
    const minMonth = `${nextMonth.getFullYear()}-${String(nextMonth.getMonth() + 1).padStart(2, '0')}`;
    expiryDateInput.min = minMonth;

    form.addEventListener("submit", function (e) {
        const cardNumber = cardNumberInput.value.trim();
        const expiryDate = expiryDateInput.value.trim();
        const cvv = cvvInput.value.trim();
        const errors = [];

        // Validate card number
        if (!/^\d{16}$/.test(cardNumber)) {
            errors.push("Card number must be 16 digits.");
        }

        // Validate CVV (3 or 4 digits)
        if (!/^\d{3,4}$/.test(cvv)) {
            errors.push("CVV must be 3 or 4 digits.");
        }

        // Validate expiry date is not empty and is in the future
        if (!expiryDate) {
            errors.push("Expiry date is required.");
        } else {
            const [expYear, expMonth] = expiryDate.split("-").map(Number);
            const expDate = new Date(expYear, expMonth);
            if (expDate <= today) {
                errors.push("Expiry date must be in the future.");
            }
        }

        // Display errors
        if (errors.length > 0) {
            e.preventDefault();
            if (errorContainer) {
                errorContainer.innerHTML = errors.join("<br>");
                errorContainer.style.display = "block";
            }
        }
    });
};
