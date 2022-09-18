import {useSearchParams} from "react-router-dom";

function OAuthGithubCallback() {
    const [searchParams] = useSearchParams();
    const code = searchParams.get("code");

    var requestOptions = {
        method: 'POST',
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({"code": code}),
        credentials: 'include'
    };

    fetch(`${process.env.REACT_APP_API_HOST}/api/auth/login`, requestOptions)
        .then(response => window.location.href = '/')
        .catch(error => console.log('error', error));

    return (
        <div className="App">
            <header className="App-header">
                로그인 중입니다
            </header>
        </div>
    );
}

export default OAuthGithubCallback;
