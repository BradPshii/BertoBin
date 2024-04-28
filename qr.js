const wrapper1 = document.querySelector(".wrapper1");
const nameInput = wrapper1.querySelector(".name-input");
const emailInput = wrapper1.querySelector(".email-input");
const studentIdInput = wrapper1.querySelector(".student-id-input");
const yearInput = wrapper1.querySelector(".year-input");
const courseInput = wrapper1.querySelector(".course-input");
const generateBtn = wrapper1.querySelector(".generate-btn");
const qrImg = wrapper1.querySelector(".qr-code img");
let preValue;

generateBtn.addEventListener("click", () => {
    let nameValue = nameInput.value.trim();
    let emailValue = emailInput.value.trim();
    let studentIdValue = studentIdInput.value.trim();
    let yearValue = yearInput.value.trim();
    let courseValue = courseInput.value.trim();
    let qrValue = `${nameValue}, ${emailValue}, ${studentIdValue}, ${yearValue}, ${courseValue}`;

    if (!nameValue || !emailValue || !studentIdValue || !yearValue || !courseValue || preValue === qrValue) return;
    preValue = qrValue;
    preStudentIdValue = studentIdValue;
    generateBtn.innerText = "Generating QR Code...";
    qrImg.src = `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${qrValue}`;
    qrImg.addEventListener("load", () => {
        wrapper1.classList.add("active");
        generateBtn.innerText = "Generate QR Code";
    });
});

nameInput.addEventListener("keyup", () => {
    if (!nameInput.value.trim()) {
        wrapper1.classList.remove("active");
        preValue = "";
    }
});
