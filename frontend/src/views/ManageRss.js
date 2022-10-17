import BottomNavBar from "../components/BottomNavBar";
import Rss from "../components/Rss";

export default function ManageRss(props) {

    return (
        <div className="App">
            <header className="App-header">
                <Rss></Rss>
            </header>
            <footer className="App-footer">
                <BottomNavBar
                    loginStatus={props.loginStatus}
                    userInfo={props.userInfo}
                    navIndex={props.navIndex}/>
            </footer>
        </div>
    )
}
