


    function authenticateUser() {
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        const data = {
            email: email,
            password: password
        };

        fetch('/api/v1/auth/authenticate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка аутентификации');
            }
            return response.json();
        })
        .then(data => {
            if (data.access_token) {
                localStorage.removeItem('token'); // Удаляем старый токен
                localStorage.setItem('token', data.access_token); // Сохраняем новый токен
                document.getElementById('loginForm').style.display = 'none';
                document.getElementById('accountMenu').style.display = 'block';

                fetchUserInfo();
            } else {
                alert('Ошибка аутентификации: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Ошибка при отправке запроса:', error);
        });
        closeLoginPopup();
        redirectToSectionWithToken();
    }


function redirectToSectionWithToken() {
    const token = localStorage.getItem('token');
    const headers = new Headers();
    headers.append("Authorization", `Bearer ${token}`);
    window.location.href = "/";
    
}

    // Проверяем наличие токена и его срок действия при загрузке страницы
    document.addEventListener('DOMContentLoaded', () => {
        autoAuthenticateUser();
    });



    function autoAuthenticateUser() {
        const token = localStorage.getItem('token');
        if (token) {
            fetchUserInfo();
        } else {
            document.getElementById('loginForm').style.display = 'block';
            document.getElementById('accountMenu').style.display = 'none';
        }
    }



    function logoutUser() {
        const token = localStorage.getItem('token');
        localStorage.removeItem('token');
        alert('Вы успешно вышли из аккаунта');


        document.getElementById('accountMenu').style.display = 'none';
        document.getElementById('loginForm').style.display = 'block';

        fetch('/api/v1/auth/logout', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при выполнении логаута');
            }

            alert('Вы успешно вышли из аккаунта');
            window.location.href = "/";
        })
        .catch(error => {
            console.error('Ошибка при выполнении логаута:', error);
        });
    }





function redirectVivienda() {
  const token = localStorage.getItem('token');
  if (token) {
    window.location.href = "/private/Vivienda?token=" + encodeURIComponent(token);
  } else {
    window.location.href = "/public/Vivienda";
  }
}
function redirectLocal() {
  const token = localStorage.getItem('token');
  if (token) {
    window.location.href = "/private/Local?token=" + encodeURIComponent(token);
  } else {
    window.location.href = "/public/Local";
  }
}

function redirectTerrenos() {
  const token = localStorage.getItem('token');
  if (token) {
    window.location.href = "/private/Terrenos?token=" + encodeURIComponent(token);
  } else {
    window.location.href = "/public/Terrenos";
  }
}
function redirectContactos() {
  const token = localStorage.getItem('token');
  if (token) {
    window.location.href = "/private/Contactos?token=" + encodeURIComponent(token);
  } else {
    window.location.href = "/public/Contactos";
  }
}

function redirectServicios() {
  const token = localStorage.getItem('token');
  if (token) {
    window.location.href = "/private/Servicios?token=" + encodeURIComponent(token);
  } else {
    window.location.href = "/public/Servicios";
  }
}

function redirectCard(id) {
  const token = localStorage.getItem('token');
  if (token) {
    window.location.href = `/private/Vivienda/${encodeURIComponent(id)}?token=${encodeURIComponent(token)}`;
  } else {
    window.location.href = `/public/Vivienda/${encodeURIComponent(id)}`;
  }
}

function redirectCardTer(id) {
  const token = localStorage.getItem('token');
  if (token) {
    window.location.href = `/private/Terrenos/${encodeURIComponent(id)}?token=${encodeURIComponent(token)}`;
  } else {
    window.location.href = `/public/Terrenos/${encodeURIComponent(id)}`;
  }
}
function redirectCardLoc(id) {
    const token = localStorage.getItem('token');
    if (token) {
      window.location.href = `/private/Local/${encodeURIComponent(id)}?token=${encodeURIComponent(token)}`;
    } else {
      window.location.href = `/public/Local/${encodeURIComponent(id)}`;
    }
  }


document.addEventListener('DOMContentLoaded', function() {
        const prev = document.getElementById('prev-btn')
        const next = document.getElementById('next-btn')
        const list = document.getElementById('item-list')

        const itemWidth = 150
        const padding = 10

        prev.addEventListener('click', () => {
            list.scrollLeft -= itemWidth + padding
        })

        next.addEventListener('click', () => {
            list.scrollLeft += itemWidth + padding
        })
    });
    document.addEventListener('DOMContentLoaded', function() {
            const prev = document.getElementById('prev-btnt')
            const next = document.getElementById('next-btnt')
            const list = document.getElementById('item-listt')

            const itemWidth = 150
            const padding = 10

            prev.addEventListener('click', () => {
                list.scrollLeft -= itemWidth + padding
            })

            next.addEventListener('click', () => {
                list.scrollLeft += itemWidth + padding
            })
        });
        document.addEventListener('DOMContentLoaded', function() {
            const prev = document.getElementById('prev-btnl')
            const next = document.getElementById('next-btnl')
            const list = document.getElementById('item-listl')

            const itemWidth = 150
            const padding = 10

            prev.addEventListener('click', () => {
                list.scrollLeft -= itemWidth + padding
            })

            next.addEventListener('click', () => {
                list.scrollLeft += itemWidth + padding
            })
        });


        function openLoginPopup() {
            document.getElementById("loginPopup").style.display = "block";
        }
        
        function closeLoginPopup() {
            document.getElementById("loginPopup").style.display = "none";
        }


        document.addEventListener("DOMContentLoaded", function() {
            var priceElements = document.querySelectorAll(".card-precio");
            priceElements.forEach(function(element) {
                var price = parseFloat(element.textContent);
                if (!isNaN(price)) {
                    var formattedPrice = price.toLocaleString("en-US", {
                        style: "currency",
                        currency: "EUR",
                        minimumFractionDigits: 0,
                        maximumFractionDigits: 0 
                    });
                    element.textContent = formattedPrice;
                }
            });
        });
        
        
        
        
        
        