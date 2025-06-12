import { usePlayersQuery } from "@/lib/api/queries"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "./ui/table";
import { Button } from "./ui/button";
import { NavLink } from "react-router";
import { useUpdatePlayerMutation } from "@/lib/api/mutations";
import type { components, paths } from "@/lib/api/v1";
import { BanPlayerButton } from "./players/actions/BanPlayerButton";

export default function PlayersList() {
  const players = usePlayersQuery();

  return players.data && <>
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>ID</TableHead>
          <TableHead>Username</TableHead>
          <TableHead>Actions</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {
          players.data.map(player =>
            <TableRow>
              <TableCell>{player.id}</TableCell>
              <TableCell>{player.username}</TableCell>
              <TableCell className="flex gap-2 flex-wrap">
                <NavLink to={`/players/${player.id}`}>
                  <Button size={"sm"}>View player</Button>
                </NavLink>
                <BanPlayerButton player={player} />
              </TableCell>
            </TableRow>
          )
        }
      </TableBody>
    </Table>
  </>
}