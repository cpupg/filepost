fetch('http://localhost:8080/list')
    .then(value => {
        console.log(value);
    })
    .catch(error => {
        console.error(error);
    })
