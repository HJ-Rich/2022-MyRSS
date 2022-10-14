import {BottomNavigation, BottomNavigationAction, Box, Paper} from "@mui/material";
import {useState} from "react";
import {Link} from "react-router-dom";
import HomeIcon from '@mui/icons-material/Home';
import LibraryAddCheckIcon from '@mui/icons-material/LibraryAddCheck';
import BookmarksIcon from '@mui/icons-material/Bookmarks';
import ForumIcon from '@mui/icons-material/Forum';
import MenuIcon from '@mui/icons-material/Menu';

export default function BottomNavBar(props) {
    const [value, setValue] = useState(props.navIndex);

    return (
        <Box sx={{width: 500}}>
            <Paper sx={{position: 'fixed', bottom: 0, left: 0, right: 0}} elevation={3}>
                <BottomNavigation
                    showLabels
                    value={value}
                    onChange={(event, value) => {
                        setValue(value);
                    }}
                >
                    <BottomNavigationAction component={Link} to={"/"} label="Home" icon={<HomeIcon/>}/>
                    <BottomNavigationAction component={Link} to={"/social"} label="Social" icon={<ForumIcon/>}/>
                    <BottomNavigationAction component={Link} to={"/subscribed"} label="Subscribed"
                                            icon={<LibraryAddCheckIcon/>}/>
                    <BottomNavigationAction component={Link} to={"/bookmarks"} label="Bookmarks"
                                            icon={<BookmarksIcon/>}/>
                    <BottomNavigationAction component={Link} to={"/menu"} label="Menu" icon={<MenuIcon/>}/>
                </BottomNavigation>
            </Paper>
        </Box>
    );
}
