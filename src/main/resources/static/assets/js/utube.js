document.getElementById('findUtubeList')
    .addEventListener('click', function(event) {
        event.preventDefault(); // form의 기본 동작 action을 막음
        window.location.reload();
    });

document.getElementById('processCrawling')
    .addEventListener('click', function(event) {
        event.preventDefault(); // form의 기본 동작 action을 막음
        alert("todo: crawling server event");
    });

const updateShownUtube = (utuberId, utubeId, utubeUrl) => {
    fetch(
        host + '/api/utubes/'+ utuberId + '/utube/' + utubeId,
        {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("utube 시청 이력 기록 실패");
            }
        })
        .then(() => {
            window.open(utubeUrl, '_blank');
            window.location.reload();
        })
        .catch(error => {
            console.error('Fetch error:', error)});
}