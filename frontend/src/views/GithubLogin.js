import {GithubLoginButton} from "react-social-login-buttons";
import BottomNavBar from "../components/BottomNavBar";
import UnauthorizedMessage from "../components/UnauthorizedMessage";

export default function GithubLogin(props) {
    return (
        <div className="App">
            <header className="App-header">
                <a href={`https://github.com/login/oauth/authorize?client_id=${process.env.REACT_APP_GITHUB_OAUTH_CLIENT_ID}`}>
                    <GithubLoginButton text={'Sign in with Github'} style={{maxWidth: '230px'}}
                                       align={'center'}/>
                </a>
            </header>
            {
                props.needAlert === true ?
                    <UnauthorizedMessage message={'로그인이 필요한 기능이에요 😃'} level={'error'}/>
                    : <div></div>
            }
            <footer className="App-footer">
                <BottomNavBar loginStatus={props.loginStatus} navIndex={props.navIndex}/>
            </footer>
        </div>
    )
}
