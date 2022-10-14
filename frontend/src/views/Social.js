import BottomNavBar from "../components/BottomNavBar";

export default function Social(props) {
    return (
        <div className="App">
            <header className="App-header">
                <div>
                    <img src="/construction.png" style={{top: '40vh', width: '100%', maxWidth: '700px'}}/>
                </div>
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
