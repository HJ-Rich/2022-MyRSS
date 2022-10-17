import BottomNavBar from "../components/BottomNavBar";
import MenuView from "./MenuView";

export default function Menu(props) {
    return (
        <div className="App">
            <header className="App-header">
                <MenuView
                    loginStatus={props.loginStatus}
                    userInfo={props.userInfo}/>
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
