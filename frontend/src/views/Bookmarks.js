import BottomNavBar from "../components/BottomNavBar";
import BookmarkFeeds from "./BookmarkFeeds";

export default function Bookmarks(props) {

    return (
        <div className="App">
            <header className="App-header">
                <BookmarkFeeds
                    fetchOption={props.fetchOption}/>
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
