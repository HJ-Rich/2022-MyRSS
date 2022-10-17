import SubscribedFeeds from "./SubscribedFeeds";
import BottomNavBar from "../components/BottomNavBar";
import {Button} from "@mui/material";
import RssFeedIcon from "@mui/icons-material/RssFeed";

export default function Subscribed(props) {
    return (
        <div className="App">
            <header className="App-header">
                <a href={'/rss'} style={{marginTop: 20}}>
                    <Button variant="outlined" color="success"><RssFeedIcon/> &emsp;Manage RSS</Button>
                </a>
                <SubscribedFeeds/>
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
