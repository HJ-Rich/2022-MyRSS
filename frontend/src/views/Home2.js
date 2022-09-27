import {useEffect, useState} from "react";
import DefaultFeeds from "./DefaultFeeds";
import BottomNavBar from "../components/BottomNavBar";
import axios from "axios";

export default function Home2(props) {
    const [loginStatus, setLoginStatus] = useState();

    useEffect(() => {
        axios.post(`${process.env.REACT_APP_API_HOST}/api/auth/certificate`,
            {}, {withCredentials: true})
            .then(({data}) => setLoginStatus(data.loggedIn))
    }, [])

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
                <DefaultFeeds fetchOption={props.fetchOption}/>
            </header>
            <footer className="App-footer">
                <BottomNavBar navIndex={props.navIndex}/>
            </footer>
        </div>
    );
}
