const BASE_API = "http://localhost:8080";
const employModal = new bootstrap.Modal(document.getElementById("employeeModal"));

// this is a part of search input box
let allEmploy=[];

// fetching employs
async function fetchEmploy() {
    try{
        const response = await fetch(`${BASE_API}/employ`);
        if(!response.ok){
            throw new Error(`HTTP error status:${response.status}`);
        }
        const employ = await response.json();
        allEmploy = employ;
        showEmploy(employ);
    }catch(e){
        console.log("Failed to fetch employ ",e);
    }
}


// show employ
async function showEmploy(employ){
    const employTableBody = document.getElementById("employTableBody");
    employTableBody.innerHTML = "";

    employ.forEach(employee=>{
        const row = document.createElement("tr");
        // <td>${employee.id}</td>
        row.innerHTML = `
            
            <td>${employee.firstName}</td>
            <td>${employee.lastName}</td>
            <td>${employee.primaryNumber}</td>
            <td>${employee.alternateNumber}</td>
            <td>${employee.email}</td>
            <td>${employee.dateOfJoining}</td>
            <td>${employee.salary}</td>
            <td>${employee.department ? employee.department.name : 'N/A'}</td>
            <td>
                <button class="btn btn-warning btn-sn" onclick="editEmploy(${employee.id})">Edit</button>
                <button class="btn btn-warning btn-sn" onclick="deleteEmploy(${employee.id})">Delete</button>
            </td>
        `;
        employTableBody.appendChild(row);
    });
}

// edit employ
async function editEmploy(id){
    try{
        const response = await fetch(`${BASE_API}/employ/${id}`);
        if(!response.ok){
            throw new Error(`HTTP error status:${response.status}`);
        }

        const employ = await response.json();

        document.getElementById('employID').value = employ.id;
        document.getElementById('firstName').value = employ.firstName;
        document.getElementById('lastName').value = employ.lastName;
        document.getElementById('primaryContact').value = employ.primaryNumber;
        document.getElementById('alternateContact').value = employ.alternateNumber;
        document.getElementById('email').value = employ.email;
        document.getElementById('dateOfJoining').value = employ.dateOfJoining;
        document.getElementById('salary').value = employ.salary;
        
        // new department
        if(employ.department){
            document.getElementById('departmentName').value = employ.department.name;
            document.getElementById('departmentRole').value = employ.department.role;
            document.getElementById('departmentLocation').value = employ.department.location;
        }else{
            document.getElementById('departmentName').value = '';
            document.getElementById('departmentRole').value = '';
            document.getElementById('departmentLocation').value = '';
        }

        employModal.show();
    }catch(e){
        console.error("failed to fetch employ for editing ",e);
    }
}

// delete employ
async function deleteEmploy(id){
    if(!confirm("Are you sure you want to delete the employ ")){
        return;
    }
    try{
        const response = await fetch(`${BASE_API}/employ/${id}`,{
            method:'DELETE'
        });
        if(response.ok){
            fetchEmploy();
        }else{
            alert("Failed to delete employee")
        }

    }catch(e){
        console.error('Failed to delete employ ',e);
    }
}

// create or update employ

document.getElementById('employForm').addEventListener('submit',async function(event){
    event.preventDefault();

    const employData = {
        id:document.getElementById('employID').value,
        firstName:document.getElementById('firstName').value,
        lastName:document.getElementById('lastName').value,
        primaryNumber:document.getElementById('primaryContact').value,
        alternateNumber:document.getElementById('alternateContact').value,
        email:document.getElementById('email').value,
        dateOfJoining:document.getElementById('dateOfJoining').value,
        salary:document.getElementById('salary').value,
        department:{
            name: document.getElementById('departmentName').value,
            role: document.getElementById('departmentRole').value,
            location: document.getElementById('departmentLocation').value
        }
    };

    const method = employData.id ? 'PUT':'POST';
    
    const url = employData.id  ?  `${BASE_API}/employ/${employData.id}`:`${BASE_API}/employ`;
    
    try{
        const response = await fetch(url,{
            method,
            headers:{ 'Content-Type': 'application/json' },
            body:JSON.stringify(employData),
        });

            console.log(employData);
        if(response.ok){
            employModal.hide();
            fetchEmploy();
        }else{
            const errorText = await response.text(); // Get error message from the response
            console.error("Failed to save employ data:", errorText);
            alert("Failed to save employ data");
        }
    }catch(e){
        console.error("Failed to save employ ",e);
    }

})


// create employ button
document.getElementById('createEmployeeBtn').addEventListener("click", function(){
    
    document.getElementById('employID').value = '';
    document.getElementById('firstName').value = '';
    document.getElementById('lastName').value = '';
    document.getElementById('primaryContact').value = '';
    document.getElementById('alternateContact').value = '';
    document.getElementById('email').value = '';
    document.getElementById('dateOfJoining').value = '';
    document.getElementById('salary').value = '';
    document.getElementById('departmentName').value = '';
    document.getElementById('departmentRole').value = '';
    document.getElementById('departmentLocation').value = '';


    employModal.show();
})


// sort employ by name

let sortOrder = 'asc';

function sortEmployByName(){
    const employTableBody = document.getElementById('employTableBody');
    const rows = Array.from(employTableBody.querySelectorAll('tr'));

    // sorting
    rows.sort((rowA,rowB)=>{
        const nameA =rowA.cells[0].textContent.toLowerCase();
        const nameB = rowB.cells[0].textContent.toLowerCase();

        if(nameA<nameB){
            return sortOrder === 'asc'? -1:1;
        }
        if(nameA>nameB){
            return sortOrder ==='asc'? 1:-1;
        }
        return 0;
    });

    sortOrder = sortOrder=== 'asc' ? 'desc':'asc';
    employTableBody.innerHTML = "";
    rows.forEach(row => {employTableBody.appendChild(row)});
}

// searching through input bar

document.getElementById('search').addEventListener("input", function(){
    const searchTerm = this.value.toLowerCase().trim();
    const searchTerms = searchTerm.split(/\s+/);

    const filteredEmploy = allEmploy.filter(employ=>{
        const firstNameMatch =searchTerms.length > 0 ? employ.firstName.toLowerCase().includes(searchTerms[0]) : true; 
        const lastNameMatch = searchTerms.length > 1 ? employ.lastName.toLowerCase().includes(searchTerms[1]) : true;

        return firstNameMatch && lastNameMatch ;
    })     
    showEmploy(filteredEmploy);
})

fetchEmploy();