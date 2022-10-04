import {Box, CircularProgress} from "@mui/material";

export default function LoadingSpinner() {
    return (
        <Box sx={{display: 'flex'}}>
            <CircularProgress size={'3rem'}/>
        </Box>
    );
}
