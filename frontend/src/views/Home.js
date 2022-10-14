import DefaultFeeds from "./DefaultFeeds";
import BottomNavBar from "../components/BottomNavBar";

export default function Home(props) {
    return (
        <div className="App">
            <header className="App-header">
                <DefaultFeeds/>
            </header>
            <footer className="App-footer">
                <BottomNavBar
                    loginStatus={props.loginStatus}
                    userInfo={props.userInfo}
                    navIndex={props.navIndex}/>
            </footer>
        </div>
    );
}
