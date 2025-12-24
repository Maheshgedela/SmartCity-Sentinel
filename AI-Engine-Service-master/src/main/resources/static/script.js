function loadData() {
    fetch('http://localhost:8081/api/ai/history') // Gateway URL
        .then(response => response.json())
        .then(data => {
            let rows = "";
            data.forEach(item => {
                rows += `<tr>
                    <td>${item.areaName}</td>
                    <td>${item.vehicleCount}</td>
                    <td>${item.recordedAqi}</td>
                    <td class="${item.alertLevel}">${item.alertLevel}</td>
                    <td>${item.healthAdvice}</td>
                </tr>`;
            });
            document.getElementById('tableBody').innerHTML = rows;
        })
        .catch(err => alert("Gateway is not responding! Check 503 error."));
}

window.onload = loadData;