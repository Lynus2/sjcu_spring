const host = 'http://localhost:3002'

const token = localStorage.getItem('grantType') + ' ' + localStorage.getItem('accessToken');

getData = async (url) => {
    return fetch(
        url,
        {
            method: 'get',
            headers: {'Content-Type': 'application/json'},
        })
        .then((response) => {
            return response.json();
        })
        .catch((error) => {
            console.log(error);
        });
};

postData = (url, bodyData) => {
    return fetch(
        url,
        {
            method: 'post',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(bodyData)
        })
        .then((response) => {
            return response.json();
        })
        .catch((error) => {
            console.log(error);
        });
};

putData = (url, bodyData) => {
    return fetch(
        url,
        {
            method: 'put',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(bodyData)
        })
        .then((response) => {
            return response.json();
        })
        .catch((error) => {
            console.log(error);
        });
};

patchData = (url, bodyData) => {
    const param = {
        method: 'PATCH',
        headers: {'Content-Type': 'application/json'},
    };
    if (bodyData !== undefined){
        param.body = JSON.stringify(bodyData);
    }

    return fetch(
        url,
        param)
        .then((response) => {
            return response.json();
        })
        .catch((error) => {
            console.log(error);
        });
};

deleteData = (url) => {
    return fetch(
        url,
        {
            method: 'delete',
            headers: {'Content-Type': 'application/json'},
        })
        .then((response) => {
            return response.json();
        })
        .catch((error) => {
            console.log(error);
        });
};

const loadingImage = (boolean) => {
    const overlay = document.getElementById('loading');
    overlay.style.display = boolean ? 'flex' : 'none';
};