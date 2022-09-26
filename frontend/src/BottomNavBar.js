import RestoreIcon from '@mui/icons-material/Restore';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ArchiveIcon from '@mui/icons-material/Archive';

import {BottomNavigation, BottomNavigationAction, Box, CssBaseline, Paper} from "@mui/material";

function BottomNavBar() {
    return (
        <Box sx={{pb: 7}}>
            <CssBaseline/>
            <Paper sx={{position: 'fixed', bottom: 0, left: 0, right: 0}} elevation={3}>
                <BottomNavigation
                    showLabels
                    onChange={(event, newValue) => {
                        console.log('button clicked')
                    }}
                >
                    <BottomNavigationAction label="Recents" icon={<RestoreIcon/>}/>
                    <BottomNavigationAction label="Favorites" icon={<FavoriteIcon/>}/>
                    <BottomNavigationAction label="Archive" icon={<ArchiveIcon/>}/>
                </BottomNavigation>
            </Paper>
        </Box>
    );
}

export default BottomNavBar;
