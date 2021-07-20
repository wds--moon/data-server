import { ds } from '@/types/service'
import MySQL8 from './mysql8'
import SQLSERVER from './sqlserver'

export default {
  [ds.DbTypes.MySQL8]: MySQL8,
  [ds.DbTypes.SQLSERVER]: SQLSERVER
}
