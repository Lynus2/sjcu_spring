window.onload = async () => {
    findUtuberCategoryList();
};

const findUtuberCategoryList = () => {
    fetch(
        '/api/utubes/category',
        {
            method: 'get',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            }
        })
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            document.getElementById('categoryList').innerHTML = '';
            for (category of data) {
                let html = '<option value='
                    + category.categoryId
                    + '>'
                    + category.categoryName
                    + '</option>';
                document.getElementById('categoryList')
                    .insertAdjacentHTML('beforeend', html);
            }
        })
        .catch((error) => {
            console.log(error);
        });
};

const updateUtuberUse = (utuberId) => {
    fetch(
        host + '/api/utubes/'+ utuberId + '/use',
        {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            }
        })
        .then(response => {
            if (response.status === 401) {
                throw new Error("Expired JWT Token");
            }

            if (!response.ok) {
                throw new Error("utuber 비활성화 실패");
            }
        })
        .then(() => {window.location.reload()})
        .catch(error => {
            console.error('Fetch error:', error)});
}

const updateUtuberDisUse = (utuberId) => {
    fetch(
        host + '/api/utubes/'+ utuberId + '/disuse',
        {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            }
        })
        .then(response => {
            if (response.status === 401) {
                throw new Error("Expired JWT Token");
            }

            if (!response.ok) {
                var a = response.body;
                throw new Error("utuber 비활성화 실패");
            }
        })
        .then(() => {window.location.reload()})
        .catch(error => {
            console.error('Fetch error:', error)});
}

switchCreateUtuberModal = async (method, utuberId, utuberName, categoryName, utuberUrl) => {
    let status = document.getElementById('modalSignin').style.display;

    if (method === 'close' && status === 'block') {
        document.getElementById('utuber').value = '';
        document.getElementById('url').value = '';
        document.getElementById('modalSignin').style.display = 'none';
    }

    if (status === 'none' || status === '') {
        let button = '';
        let title = '';
        if (method === 'create') {
            title = '등록';
            button = '<button type="button"' +
                ' onclick="createUtuber()"' +
                ' class="w-100 mb-2 btn btclassNamerounded-3 btn-primary">등록</button>';
        } else if (method === 'modify') {
            title = '수정';
            button = '<button type="button" onclick="updateUtuber()" class="w-100 mb-2 btn btclassNamerounded-3 btn-primary">수정</button>';

            const selectElement = document.getElementById('categoryList');

            for (let i = 0; i < selectElement.options.length; i++) {

                if (selectElement.options[i].value === categoryName) {
                    selectElement.options[i].selected = true;
                    break;
                }
            }
            document.getElementById('utuberId').value = utuberId;
            document.getElementById('utuber').value = utuberName;
            document.getElementById('url').value = utuberUrl;
        }
        document.getElementById('modalTitle').innerText = title;
        document.getElementById('modalButtonArea').innerHTML = button;

        document.getElementById('modalSignin').style.display = 'block';
    }

    document.getElementById('closeCreateUtuberModal').addEventListener('click', () => {
        switchCreateUtuberModal('close');
    });
}

const updateUtuber = () => {
    var utuberId = document.getElementById('utuberId').value;

    fetch(
        host + '/api/utubes/'+ utuberId,
        {
            method: 'PUT',
            headers: {'Content-Type': 'application/json',
                'Authorization': token},
            body: JSON.stringify({
                "categoryId": document.getElementById('categoryList').value,
                "utuberName": document.getElementById('utuber').value,
                "utuberUrl": document.getElementById('url').value})})
        .then(() => {
            window.location.reload()})
        .catch((error) => {
            console.log(error);
        });
}

const createUtuber = () => {
    fetch(
        host + '/api/utubes',
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json',
                'Authorization': token},
            body: JSON.stringify({
                "categoryId": document.getElementById('categoryList').value,
                "utuberName": document.getElementById('utuber').value,
                "utuberUrl": document.getElementById('url').value})})
        .then(() => {
            window.location.reload()})
        .catch((error) => {
            console.log(error);
        });
}

const deleteUtuber = (utuberId) => {
    fetch(
        host + '/api/utubes/' + utuberId,
        {
            method: 'DELETE',
            headers: {'Content-Type': 'application/json',
                'Authorization': token}})
        .then(() => {
            window.location.reload()})
        .catch((error) => {
            console.log(error);
        });
}