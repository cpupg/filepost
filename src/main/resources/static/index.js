let fileList;
fetch('http://localhost:8080/list')
    .then(value => {
        return value.json();
    }).then(value => {
    console.log(value);
    fileList = value;
    let dom = document.getElementById("fileList");
    if (fileList != null && Array.isArray(fileList)) {
        fileList.forEach(f => {
            let link = document.createElement("a");
            link.href = `http://localhost:8080/download/${f}`;
            link.text = f;
            let li = document.createElement("li");
            li.append(link);
            dom.append(li);
        })
    }
})
    .catch(error => {
        console.error(error);
    })
