import {GithubLoginButton} from "react-social-login-buttons";
import BottomNavBar from "../components/BottomNavBar";

export default function GithubLogin(props) {
    return (
        <div className="App">
            <header className="App-header">
                <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.REACT_APP_GITHUB_OAUTH_CLIENT_ID}`}>
                    <GithubLoginButton text={'Sign in with Github'} style={{maxWidth: '230px'}}
                                       align={'center'}/>
                </a>
            </header>
            <footer className="App-footer">
                <BottomNavBar loginStatus={props.loginStatus} navIndex={props.navIndex}/>
            </footer>
        </div>
    )
}
