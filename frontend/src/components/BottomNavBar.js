import {BottomNavigation, BottomNavigationAction, Box, Paper} from "@mui/material";
import {useState} from "react";
import {Link} from "react-router-dom";
import HomeIcon from '@mui/icons-material/Home';
import GitHubIcon from '@mui/icons-material/GitHub';
import SearchIcon from '@mui/icons-material/Search';
import LibraryAddCheckIcon from '@mui/icons-material/LibraryAddCheck';
import BookmarksIcon from '@mui/icons-material/Bookmarks';

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
                    <BottomNavigationAction component={Link} to={"/search"} label="Search" icon={<SearchIcon/>}/>
                    <BottomNavigationAction component={Link} to={"/subscribed"} label="Subscribed"
                                            icon={<LibraryAddCheckIcon/>}/>
                    <BottomNavigationAction component={Link} to={"/bookmarks"} label="Bookmarks"
                                            icon={<BookmarksIcon/>}/>
                    <BottomNavigationAction component={Link} to={"/profile"} label="Profile" icon={<GitHubIcon/>}/>
                </BottomNavigation>
            </Paper>
        </Box>
    );
}
