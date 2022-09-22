import {useState} from "react";
import DefaultFeeds from "./DefaultFeeds";

function Home() {
    const [loginStatus, setLoginStatus] = useState();

    fetch(`${process.env.REACT_APP_API_HOST}/api/auth/certificate`,
        {method: 'POST', credentials: 'include'})
        .then(result => result.json())
        .then(json => setLoginStatus(json.loggedIn));

    return (
        <div className="App">
            <header className="App-header">
                {loginStatus === true ?
                    <div> you've logged in! </div>
                    :
                    < button type="button">
                        <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.REACT_APP_GITHUB_OAUTH_CLIENT_ID}`}>
                            Sign in with Github
                        </a>
                    </button>
                }
                <DefaultFeeds/>
            </header>
        </div>
    );
}

export default Home;
